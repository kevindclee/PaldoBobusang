import React from 'react'
import styled from 'styled-components';
import { Line } from 'react-chartjs-2';

const AgriChart = (props) => {

  const data = {
    labels: ['1년전', '6달 전', '3달 전', '1달 전', '1주일 전', '당일'],
    datasets: [
      {
        type: 'line',
        label: '상추',
        borderColor: 'rgb(54, 162, 235)',
        borderWidth: 2,
        data: [1, 2, 3, 4, 5, 6],
      }
    ]
  }

  const Container = styled.div`
  width: 90vw;
  max-width: 900px;
  `;

  return (
    <>
      <div className={props.className}></div>
      <Container>
        <Line data={data} />
      </Container>
    </>
  );
};

export default AgriChart