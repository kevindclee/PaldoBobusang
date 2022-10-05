import React from 'react'
import styles from '../../styles/agri-detail.module.css';

const AgriLocation = (props) => {
  const agriLoc = props.object.locations;
  const localList = {'수도권': [],
                     '강원도': [],
                     '충청북도': [],
                     '충청남도': [],
                     '전라북도': [],
                     '전라남도': [],
                     '경상북도': [],
                     '경상남도': [],
                     '제주도': [],
                    }
  agriLoc.map(item => localList[item.localName].push(item.cityName));

  const localKey = Object.keys(localList);

  return (
    <div className={`${props.className} ${styles['agri-loc-frame']}`}>
      <table className={styles['agri-loc-table']}>
        <tr><th>도이름</th><th>시이름</th></tr> 
          {
            localKey.map(key => {
              return (
                localList[key].map((city, index) => {
                  return index === 0 ? <tr>
                                          <td rowSpan={localList[key].length}>{key}</td>
                                          <td>{city}</td>
                                       </tr>:
                                       <tr>
                                          <td>{city}</td>
                                       </tr>
                })  
              )
              
            })
          }
      </table>
    </div>
  )
}


export default AgriLocation