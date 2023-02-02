import { useAtom } from "jotai";
import React, { useEffect } from "react";
import AgriMap from "../../components/agri-map/AgriMap";
import AgriInfo from "../../components/agri-map/AgriInfo";
import curLocationAtom from "../../atoms/CurLocationAtom";
import styles from "../../styles/agri-map-index.module.css";
import mobile from "../../styles/mobile/agri-map-index.module.css";
import curAppAtom from "../../atoms/CurAppAtom";
import curProductListAtom from "../../atoms/CurProductList";
import { useMediaQuery } from "react-responsive";

const index = () => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);
  const [curApp, setCurApp] = useAtom(curAppAtom);
  const [curProductList, setCurProductList] = useAtom(curProductListAtom);

  useEffect(() => {
    setCurApp("agri-map");
    setCurProductList(null);
  }, []);

  return isLaptop ? (
    <div className={styles.frame}>
      {curLocation === "" ? (
        <AgriMap />
      ) : (
        <>
          <AgriMap />
          <AgriInfo />
        </>
      )}
    </div>
  ) : (
    <div className={mobile.frame}>
      {curLocation === "" ? (
        <AgriMap />
      ) : (
        <div className={mobile.mapAndInfo}>
          <AgriMap />
          <AgriInfo />
        </div>
      )}
    </div>
  );
};

export default index;
