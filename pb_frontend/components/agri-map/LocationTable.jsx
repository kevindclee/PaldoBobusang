import React from "react";
import { useMediaQuery } from "react-responsive";
import styles from "../../styles/location-table.module.css";
import mobile from "../../styles/mobile/location-table.module.css";

const LocationTable = (props) => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const array = props.array;

  return isLaptop ? (
    <div className={`cities-frame ${styles["cities-frame"]}`}>
      <table className={styles.cities}>
        <tr>
          <th>도</th>
          <th>시(군)</th>
        </tr>
        {array ? (
          array.map((city, index) => {
            return index === 0 ? (
              <tr>
                <td rowSpan={array.length}>{city.localName}</td>
                <td>{city.cityName}</td>
              </tr>
            ) : (
              <tr>
                <td>{city.cityName}</td>
              </tr>
            );
          })
        ) : (
          <></>
        )}
      </table>
    </div>
  ) : (
    <div className={`cities-frame ${styles["cities-frame"]}`}>
      <table className={styles.cities}>
        <tr>
          <th>도</th>
          <th>시(군)</th>
        </tr>
        {array ? (
          array.map((city, index) => {
            return index === 0 ? (
              <tr>
                <td rowSpan={array.length}>{city.localName}</td>
                <td>{city.cityName}</td>
              </tr>
            ) : (
              <tr>
                <td>{city.cityName}</td>
              </tr>
            );
          })
        ) : (
          <></>
        )}
      </table>
    </div>
  );
};

export default LocationTable;
