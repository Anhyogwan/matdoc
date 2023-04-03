/* eslint-disable no-unused-vars */
import React, { useEffect } from 'react';
import styled from 'styled-components';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import axios from 'axios';

import { API_URL_HOSPITAL } from '../../api/api';
import { hospitalDescState, hospitalBasicState } from '../../atoms';
import BackButton from '../../components/common/BackButton';
import KakaoMap from '../../components/kakao/KakaoMap';
import HositalTotal from '../../components/hospital/HositalTotal';

const SBackButton = styled.button`
  border: none;
  background: none;
`;

const SContainer = styled.div``;

function HospitalDetail() {
  const information = useLocation();
  const navigate = useNavigate();
  const { hospitalId } = useParams();
  const lat = information.state.information.hospitalY;
  const lng = information.state.information.hospitalX;
  const [hospitalDesc, setHospitalDesc] = useRecoilState(hospitalDescState);
  const [hospitalBasic, setHospitalBasic] = useRecoilState(hospitalBasicState);

  const getInformation = async () => {
    await axios
      .get(`${API_URL_HOSPITAL}/desc/${hospitalId}`)
      .then(
        res => setHospitalDesc(res.data.data),
        setHospitalBasic(information.state.information),
      )
      .catch(err => console.log(err));
  };

  const onClickBackButtonHandler = () => {
    navigate(-1);
  };

  useEffect(() => {
    getInformation();
  }, []);

  return (
    <>
      <SBackButton type="button" onClick={onClickBackButtonHandler}>
        <BackButton />
      </SBackButton>
      <SContainer>
        <KakaoMap lat={lat} lng={lng} />
      </SContainer>
    </>
  );
}

export default HospitalDetail;
