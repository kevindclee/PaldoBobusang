import React, { useEffect, useState } from 'react'
import styles from '../../styles/agriList.module.css';
import { Line } from 'react-chartjs-2';
import Chart from 'chart.js/auto'
import { useAtom } from 'jotai';
import curAppAtom from '../../atoms/curAppAtom';
import curLocationAtom from '../../atoms/curLocationAtom';

const AgriChart = (props) => {

  const AgriData = props.object;
  const [curApp, setCurApp] = useAtom(curAppAtom);
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);
  let productCondition = true;

  let productCode = 0;
  if(typeof AgriData.fruitCode !== 'undefined'){
    productCode = AgriData.fruitCode;
  } else{
    productCode = AgriData.vegetableCode;
    productCondition = false;
  }

  const [locationId, setLocationId] = useState(0);
  const [priceData, setPriceData] = useState([]);

  useEffect(() => {
    if (productCondition) {
      fetch(`http://localhost:8090/prices/list/fruitCode/${productCode}`)
      .then(response => response.json())
      .then(data =>  {
        if(data.length !== 0){
          setLocationId(data[0].locationId);
        } else {
          setLocationId(0);
        }
      });
    } else {
      fetch(`http://localhost:8090/prices/list/vegetableCode/${productCode}`)
      .then(response => response.json())
      .then(data =>  setLocationId(data[0].locationId));     
    }
    
  },[]);
  console.log(curApp)
  if (curApp === 'agri-list') {
    if (locationId !== 0) { 
      if(productCondition){
        fetch(`http://localhost:8090/prices/list/fruitCodeAndLocationId/${productCode}/${locationId}`)
        .then(response => response.json())
        .then(data => setPriceData(data));
      } else {
        fetch(`http://localhost:8090/prices/list/vegetableCodeAndLocationId/${productCode}/${locationId}`)
        .then(response => response.json())
        .then(data => setPriceData(data));
      }
    }
  } else {
    if(productCondition){
      fetch(`http://localhost:8090/prices/map/fruitItemNameAndLocalEngName/${AgriData.itemName}/${curLocation}`)
      .then(response => response.json())
      .then(data => setPriceData(data));
    } else {
      fetch(`http://localhost:8090/prices/map/vegetableItemNameAndLocalEngName/${AgriData.itemName}/${curLocation}`)
      .then(response => response.json())
      .then(data => setPriceData(data));
    }
  }
  console.log('priceData:', priceData);
  const dataLength = priceData.length;

  const priceDate = ['2021-10-07', '2022-09-06', '2022-09-29', '2022-10-05'];
  const priceKey = ['1년전', '1달전', '1주일전', '당일'];
  const arrPrice = {};

  for (let index = 0; index < dataLength; index++) {
    if(priceDate[index] === priceData[index].priceDate){
      if(priceData[index].priceValue) {
        console.log("inPriceData:", priceData);
        arrPrice[priceKey[index]] = priceData[index].priceValue;
      } else
        arrPrice[priceKey[index]] = 0;
      
    }
  }
  console.log('AgriData:', AgriData)
  const data = {
    labels: Object.keys(arrPrice),
    datasets: [
      {
        type: 'line',
        label: AgriData.itemName + ' - ' + AgriData.locations[0].cityName,
        borderColor: 'rgb(54, 162, 235)',
        borderWidth: 2,
        data: Object.values(arrPrice),
      }
    ]
  }

  console.log('data:', data);

  return (
    <>
      <div className={`${props.className} ${styles['agri-chart']}`}>
        <div className={styles['container']}>
          <Line data={data} className={styles['chart']}/>
        </div>
      </div>
    </>
  );
};

export default AgriChart