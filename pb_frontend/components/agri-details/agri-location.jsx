import React from 'react'
import styles from '../../styles/agri-detail.module.css';

const AgriLocation = (props) => {
  const agriLoc = props.object.locations;
  const agriLocPrice = agriLoc[0].prices[0];

  console.log(agriLocPrice);

  return (
    <div className={props.className}>
      <table className={styles['agri-loc-table']}>
        <tr><th>도이름</th><th>시이름</th><th>가격</th><th>날짜</th></tr> 
        <tr>
          <td>{agriLoc[0].localName}</td>
          <td>{agriLoc[0].cityName}</td>
          <td>{agriLocPrice.priceValue}</td>
          <td>{agriLocPrice.priceDate}</td>
        </tr>
      </table>
    </div>
  )
}

export default AgriLocation