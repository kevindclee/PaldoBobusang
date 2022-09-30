import React from 'react'
import { useAtom } from 'jotai'
import curAppAtom from '../atoms/curAppAtom'
import styles from '../styles/agri-list.module.css';
import AgriDetail from '../components/agri-details/agri-detail';
import AgriLocation from '../components/agri-details/agri-location';
import AgriChart from '../components/agri-details/agri-chart';

const ArgiList = () => {
  const [curApp, setCurApp] = useAtom(curAppAtom);
  setCurApp('agri-map')

  return (
    <div className={styles['agri-body']} >
        <div className={styles['agri-details']}>
          <AgriDetail className={styles['agri-detail']} />
          <AgriLocation className={styles['agri-location']} />
          <AgriChart className={styles['agri-chart']} /> 
      </div>
    </div>
  )
}

export default ArgiList