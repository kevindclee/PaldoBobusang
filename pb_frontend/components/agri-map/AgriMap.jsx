import { React, useEffect } from "react";
import Overall from "./Overall";
import Specific from "./Specific";
import { useAtom } from "jotai";
import curLocationAtom from "../../atoms/CurLocationAtom";
import styles from "../../styles/agri-map.module.css";
import mobile from "../../styles/mobile/agri-map.module.css";
import AgriSpot from "./AgriSpot";
import curCitiesOnProductAtom from "../../atoms/CurCitiesOnProductAtom";
import { useMediaQuery } from "react-responsive";

const AgriMap = () => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);
  const [curCitiesOnProduct, setCurCitiesOnProduct] = useAtom(
    curCitiesOnProductAtom
  );
  useEffect(() => {
    if (curLocation !== "") {
      const className = "overall_classified_svg__" + curLocation;
      const locals = document.getElementsByClassName(className);
      const overalls = document.getElementsByClassName(
        "overall_classified_svg__overall"
      );
      for (let overall of overalls) overall.style.fill = "#b2df8a";
      for (let local of locals) local.style.fill = "#aa33ff";
    }
  }, [curLocation]);

  const clickHandler = (location) => {
    if (location) {
      setCurLocation(location.replace("overall_classified_svg__", ""));
      setCurCitiesOnProduct(null);
    }
  };

  return isLaptop ? (
    <div className={styles["agri-map"]}>
      {curLocation === "" ? (
        <Overall size="large" clickHandler={clickHandler} />
      ) : (
        <>
          <Overall size="small" clickHandler={clickHandler} />
          <AgriSpot />
          <Specific />
        </>
      )}
    </div>
  ) : (
    <>
      {curLocation === "" ? (
        <div className={mobile["unclicked"]}>
          <Overall size="large" clickHandler={clickHandler} />
        </div>
      ) : (
        <div className={mobile["clicked"]}>
          <Overall size="small" clickHandler={clickHandler} />
          <AgriSpot />
          <Specific />
        </div>
      )}
    </>
  );
};

export default AgriMap;
