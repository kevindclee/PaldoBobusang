import React, { useState, useEffect } from "react";
import Agri from "../../components/agri-details/agri";
import styles from "/styles/agriList.module.css";
import mobile from "/styles/mobile/agriList.module.css";
import { useAtom } from "jotai";
import curAppAtom from "../../atoms/CurAppAtom";
import constant from "../../public/constant.json";
import { useMediaQuery } from "react-responsive";

const AgriList = (props) => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const [itemList, setItemList] = useState([]);
  const [curApp, setCurApp] = useAtom(curAppAtom);

  useEffect(() => {
    setCurApp("agri-list");
    fetch(`${constant.backend_url}/AgriList`)
      .then((response) => response.json())
      .then((data) => setItemList(data));
  }, []);

  const onClickHandler = (id) => {
    props.onClickHandler(id);
  };

  return isLaptop ? (
    <div className={styles.body}>
      <div className={styles["background-1-1"]}></div>
      <div className={styles["background-1-2"]}></div>
      <div className={styles["background-2-1"]}></div>
      <div className={styles["background-2-2"]}></div>
      <div className={styles["background-2-3"]}></div>
      <div className={styles["background-3-1"]}></div>
      <div className={styles["background-3-2"]}></div>
      <div className={styles["background-4-1"]}></div>
      <div className={styles["background-4-2"]}></div>
      <div className={styles["background-5-1"]}></div>
      <div className={styles["background-5-2"]}></div>
      <div className={styles["background-main"]}></div>
      <div className={styles["agri-list"]}>
        {itemList.map((item) => {
          return (
            <Agri
              object={item}
              onClickHandler={onClickHandler}
              key={item.itemCode}
            />
          );
        })}
      </div>
    </div>
  ) : (
    <div className={mobile.body}>
      <div className={mobile["background-1-1"]}></div>
      <div className={mobile["background-1-2"]}></div>
      <div className={mobile["background-2-1"]}></div>
      <div className={mobile["background-2-2"]}></div>
      <div className={mobile["background-2-3"]}></div>
      <div className={mobile["background-3-1"]}></div>
      <div className={mobile["background-3-2"]}></div>
      <div className={mobile["background-4-1"]}></div>
      <div className={mobile["background-4-2"]}></div>
      <div className={mobile["background-5-1"]}></div>
      <div className={mobile["background-5-2"]}></div>
      <div className={mobile["background-main"]}></div>
      <div className={mobile["agri-list"]}>
        {itemList.map((item) => {
          return (
            <Agri
              object={item}
              onClickHandler={onClickHandler}
              key={item.itemCode}
            />
          );
        })}
      </div>
    </div>
  );
};

export default AgriList;
