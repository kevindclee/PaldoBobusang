import { useAtom } from "jotai";
import React from "react";
import { useMediaQuery } from "react-responsive";
import curAppAtom from "../../atoms/CurAppAtom";
import curCitiesOnProductAtom from "../../atoms/CurCitiesOnProductAtom";
import styles from "../../styles/agri-detail.module.css";
import mobile from "../../styles/mobile/agri-detail.module.css";

const AgriLocation = (props) => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const [curApp, setCurApp] = useAtom(curAppAtom);
  const [curCitiesOnProduct, setCurCitiesOnProduct] = useAtom(
    curCitiesOnProductAtom
  );
  const agriLoc = props.object.locations;
  const localList = {
    수도권: [],
    강원도: [],
    충청북도: [],
    충청남도: [],
    전라북도: [],
    전라남도: [],
    경상북도: [],
    경상남도: [],
    제주도: [],
  };
  curApp === "agri-list"
    ? agriLoc.map((item) => localList[item.localName].push(item.cityName))
    : curCitiesOnProduct.map((item) =>
        localList[item.localName].push(item.cityName)
      );

  const localKey = Object.keys(localList);

  return isLaptop ? (
    <div className={`${props.className} ${styles["agri-loc-frame"]}`}>
      <table className={styles["agri-loc-table"]}>
        <tr>
          <th className={styles["th-left"]}>도</th>
          <th className={styles["th-right"]}>시(군)</th>
        </tr>
        {localKey.map((key) => {
          return localList[key].map((city, index) => {
            return index === 0 ? (
              <tr>
                <td
                  className={styles["local-name"]}
                  rowSpan={localList[key].length}>
                  {key}
                </td>
                <td className={styles["city-name"]}>{city}</td>
              </tr>
            ) : (
              <tr>
                <td className={styles["city-name"]}>{city}</td>
              </tr>
            );
          });
        })}
      </table>
    </div>
  ) : (
    <div className={`${props.className} ${mobile["agri-loc-frame"]}`}>
      <table className={mobile["agri-loc-table"]}>
        <tr>
          <th className={mobile["th-left"]}>도</th>
          <th className={mobile["th-right"]}>시(군)</th>
        </tr>
        {localKey.map((key) => {
          return localList[key].map((city, index) => {
            return index === 0 ? (
              <tr>
                <td
                  className={mobile["local-name"]}
                  rowSpan={localList[key].length}>
                  {key}
                </td>
                <td className={mobile["city-name"]}>{city}</td>
              </tr>
            ) : (
              <tr>
                <td className={mobile["city-name"]}>{city}</td>
              </tr>
            );
          });
        })}
      </table>
    </div>
  );
};

export default AgriLocation;
