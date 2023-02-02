import React from "react";
import { useMediaQuery } from "react-responsive";
import styles from "../../styles/agri-detail.module.css";
import mobile from "../../styles/mobile/agri-detail.module.css";

const AgriDetail = (props) => {
  const detail = props.object;
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });

  return isLaptop ? (
    <div className={`${props.className} ${styles["detail-body"]}`}>
      <div className={styles["detail-table"]}>
        <div className={styles["table-item1"]}>
          <img
            src={detail.itemImage}
            alt={detail.itemName}
            className={styles["detail-image"]}
          />
        </div>
        <div className={styles["table-item2"]}>품목</div>
        <div className={styles["table-item3"]}>수확 시작일</div>
        <div className={styles["table-item4"]}>수확 종료일</div>
        <div className={styles["table-item5"]}>{detail.itemName}</div>
        <div className={styles["table-item6"]}>{detail.harvestStart}</div>
        <div className={styles["table-item7"]}>{detail.harvestEnd}</div>
        <div className={styles["table-item8"]}>상세정보</div>
        <div className={styles["table-item9"]}>{detail.etcDetails}</div>
      </div>
    </div>
  ) : (
    <div className={`${props.className} ${mobile["detail-body"]}`}>
      <div className={mobile["detail-table"]}>
        <div className={mobile["table-item1"]}>
          <img
            src={detail.itemImage}
            alt={detail.itemName}
            className={mobile["detail-image"]}
          />
        </div>
        <div className={mobile["table-item2"]}>품목</div>
        <div className={mobile["table-item3"]}>수확 시작일</div>
        <div className={mobile["table-item4"]}>수확 종료일</div>
        <div className={mobile["table-item5"]}>{detail.itemName}</div>
        <div className={mobile["table-item6"]}>{detail.harvestStart}</div>
        <div className={mobile["table-item7"]}>{detail.harvestEnd}</div>
        <div className={mobile["table-item8"]}>상세정보</div>
        <div className={mobile["table-item9"]}>{detail.etcDetails}</div>
      </div>
    </div>
  );
};

export default AgriDetail;
