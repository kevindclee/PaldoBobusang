import React from 'react';
import styles from '../../styles/agri-detail.module.css';
import { useState } from 'react';

const AgriDetail = (props) => {
  const detail = props.object;

  return (
    <div className={`${props.className} ${styles['detail-body']}`}>
      <div className={styles['detail-table']}>
        <table className={styles['detail-inside-table']}>
          <thead>
            <tr><th>이미지</th><th>품종</th><th>수확 시작철</th><th>수확 마무리철</th><th>상세 정보</th></tr>
          </thead>
          <tr>
            <td>
              <img className={styles['detail-image']} src={detail.itemImage} />
            </td> 
            <td>{detail.itemName}</td> 
            <td>{detail.harvestStart}</td> 
            <td>{detail.harvestEnd}</td> 
            <td>{detail.etcDetails}</td>
          </tr>
        </table>
      </div>
    </div>
  )
}

export default AgriDetail