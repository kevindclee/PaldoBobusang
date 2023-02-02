import React, { useEffect, useState } from "react";
import styles from "../../styles/agriList.module.css";
import { Line } from "react-chartjs-2";
import { useAtom } from "jotai";
import curLocationAtom from "../../atoms/CurLocationAtom";
import curAppAtom from "../../atoms/CurAppAtom";
import constant from "../../public/constant.json";
import Chart from "chart.js/auto";

const AgriChart = (props) => {
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);
  const [curApp, setCurApp] = useAtom(curAppAtom);

  const AgriData = props.object;
  let productCode = AgriData.itemCode;

  const [location, setLocation] = useState(0);
  const [priceData, setPriceData] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      curApp === "agri-map" ? setLocation(curLocation) : setLocation("capital");

      const rawRes2 = await fetch(
        `${constant.backend_url}/prices/list/itemCodeAndLocationId/${productCode}/${location}`
      );
      const data = await rawRes2.json();
      setPriceData(data);
    };

    setTimeout(() => {
      fetchData();
    }, 500);
  }, [location]);

  const substractDate = (date, day) => {
    const newDate = new Date(date);
    newDate.setDate(date.getDate() - day);

    return newDate;
  };
  const today = new Date();
  const aWeekAgo = substractDate(today, 7);
  const aMonthAgo = substractDate(today, 31);
  const aYearAgo = substractDate(today, 365);
  const dateFormat = (date) => {
    let month = date.getMonth() + 1;
    let day = date.getDate();

    month = month > 10 ? month : "0" + month;
    day = day > 10 ? day : "0" + day;

    return `${date.getFullYear()}-${month}-${day}`;
  };

  const priceDate = [
    dateFormat(aYearAgo),
    dateFormat(aMonthAgo),
    dateFormat(aWeekAgo),
    dateFormat(today),
  ];
  const priceKey = ["1년전", "1달전", "1주일전", "당일"];
  const arrPrice = {};

  const dataLength = priceData.length;
  let listCityName = "";
  for (let index = 0; index < dataLength; index++) {
    for (let j = priceDate.length - 1; j >= 0; j--) {
      if (priceData[index].priceDate === priceDate[j]) {
        arrPrice[priceKey[j]] = priceData[index].priceValue;
        listCityName = priceData[index].location.localName;
      }
    }
  }
  const data = {
    labels: Object.keys(arrPrice),
    datasets: [
      {
        type: "line",
        label: AgriData.itemName + " - " + listCityName,
        borderColor: "rgb(54, 162, 235)",
        borderWidth: 2,
        data: Object.values(arrPrice),
        fill: false,
      },
    ],
  };

  return (
    <>
      <div className={`${props.className} ${styles["agri-chart"]}`}>
        <div className={styles["container"]}>
          <Line data={data} className={styles["chart"]} />
        </div>
      </div>
    </>
  );
};

export default AgriChart;
