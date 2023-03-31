package com.roller.doc.db.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roller.doc.config.QuerydslConfig;
import com.roller.doc.db.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.roller.doc.db.entity.QHospital.*;
import static com.roller.doc.db.entity.QHospitalPart.*;
import static com.roller.doc.db.entity.QHospitalTime.*;


@Repository
@RequiredArgsConstructor
public class HospitalCustomRepo {
    private final QuerydslConfig querydslConfig;

    /**
     * 이름으로 병원검색
     */
    public List<Hospital> searchByHospitalName(String word, double e, double w, double s, double n) {
        JPAQueryFactory query = querydslConfig.jpaQueryFactory();
        return query.selectFrom(hospital)
                .where(
                        keywordSearch(word),
                        locationBetween(e, w, s, n)
                )
                .fetch();
    }

    /**
     * 거리5km+진료과목+운영시간 필터
     */
    public List<Hospital> useFilterHospital(double e, double w, double s, double n, int part,
                                            int sat, int sun, int holiday, int night) {
        JPAQueryFactory query = querydslConfig.jpaQueryFactory();

        if (sat == 0 && sun == 0 && holiday == 0 && night == 0 && part == 0) { //필터가 없는데 검색누른경우
            return query.select(hospital)
                    .from(hospital)
                    .where(locationBetween(e, w, s, n)
                    ).distinct().fetch();
        }
        if (part == 0) { //진료과목 필터가 없는 경우
            return query.select(hospital)
                    .from(hospital)
                    .innerJoin(hospitalTime).fetchJoin().on((openSat(hospital.hospital_id, sat)).or(openSun(hospital.hospital_id, sun)).or(openHoliday(hospital.hospital_id, holiday)).or(openNight(hospital.hospital_id, night)))
                    .where(locationBetween(e, w, s, n)
                    ).distinct().fetch();
        }
        if (sat == 0 && sun == 0 && holiday == 0 && night == 0) { //시간 필터 없는 경우
            List<Hospital> result= query.select(hospital)
                    .from(hospital)
                    .innerJoin(hospitalPart).fetchJoin().on((partEq(hospital.hospital_id, part)))
                    .where(locationBetween(e, w, s, n)
                    ).distinct().fetch();
        }
        return query.select(hospital)
                .from(hospital)
                .innerJoin(hospitalPart).fetchJoin().on((partEq(hospital.hospital_id, part)))
                .innerJoin(hospitalTime).fetchJoin().on((openSat(hospital.hospital_id, sat)).or(openSun(hospital.hospital_id, sun)).or(openHoliday(hospital.hospital_id, holiday)).or(openNight(hospital.hospital_id, night)))
                .where(locationBetween(e, w, s, n)
                ).distinct().fetch();
    }

    /**
     * 거리 5km
     */
    public BooleanExpression locationBetween(double e, double w, double s, double n) {
        if (e == 0) return null;
        return hospital.hospital_x.between(w, e).and(hospital.hospital_y.between(s, n));
    }

    /**
     * 진료과목 필터
     */
    public BooleanExpression partEq(NumberPath<Long> id, int partName) {
        if (partName == 0) return null; //전체과목은 필터 필요없어
        return hospitalPart.hospital_part_name.eq(partName).and(hospitalPart.hospital.hospital_id.eq(id));
    }

    /**
     * 토요일 진료
     */
    public BooleanExpression openSat(NumberPath<Long> id, int sat) {
        if (sat == 0) return null;
        return hospitalTime.hospitalTimeSat.ne("null") //null이 아님=진료시간이 있음
                .and(hospitalTime.hospital.hospital_id.eq(id));
    }

    /**
     * 일요일 진료
     */
    public BooleanExpression openSun(NumberPath<Long> id, int sun) {
        if (sun == 0) return null;
        return hospitalTime.hospitalTimeSun.ne("null")
                .and(hospitalTime.hospital.hospital_id.eq(id));
    }

    /**
     * 공휴일 진료
     */
    public BooleanExpression openHoliday(NumberPath<Long> id, int holiday) {
        if (holiday == 0) return null;
        return hospitalTime.hospitalTimeHoliday.eq(1)
                .and(hospitalTime.hospital.hospital_id.eq(id));
    }

    /**
     * 야간진료
     */
    public BooleanExpression openNight(NumberPath<Long> id, int night) {
        if (night == 0) return null;
        return hospitalTime.hospitalTimeMonNight.eq(1)
                .and(hospitalTime.hospital.hospital_id.eq(id));
    }

    /**
     * 이름 fulltext search
     */
    public BooleanExpression keywordSearch(String keyword) {
        if(keyword == null) {
            return null;
        }else {
            NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,
                    "function('match',{0},{1})", hospital.hospital_name, "+" + keyword + "*");
            return booleanTemplate.gt(0);
        }
    }

}
