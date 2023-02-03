import React, { useEffect } from "react";
import styles from "/styles/agriList.module.css";
import mobile from "/styles/mobile/agriList.module.css";
import AgriDetail from "./Agri-detail";
import AgriLocation from "./Agri-location";
import AgriChart from "./Agri-chart";
import { useMediaQuery } from "react-responsive";

const AgriListDetail = (props) => {
  const agriDetail = props.object;
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });

  useEffect(() => {
    document.body.style.cssText = `position: fixed; top: -${window.scrollY}px`;
    return () => {
      const scrollY = document.body.style.top;
      document.body.style.cssText = `position: ""; top: "";`;
      window.scrollTo(0, parseInt(scrollY || "0") * -1);
    };
  }, []);

  return isLaptop ? (
    <div id="myModal" className={styles["agri-body"]}>
      <div className={styles["agri-details"]}>
        <AgriDetail object={agriDetail} className={styles["agri-detail"]} />
        <AgriLocation object={agriDetail} className={styles["agri-location"]} />
        <AgriChart object={agriDetail} className={styles["agri-chart"]} />
      </div>
    </div>
  ) : (
    <div id="myModal" className={mobile["agri-body"]}>
      <div className={mobile["agri-details"]}>
        <AgriDetail object={agriDetail} className={mobile["agri-detail"]} />
        <AgriLocation object={agriDetail} className={mobile["agri-location"]} />
        <AgriChart object={agriDetail} className={mobile["agri-chart"]} />
      </div>
    </div>
  );
};

export default AgriListDetail;
