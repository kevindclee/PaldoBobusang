import React, {useEffect} from 'react'
import styles from '/styles/agriList.module.css';
import AgriDetail from '../../components/agri-details/agri-detail';
import AgriLocation from '../../components/agri-details/agri-location';
import AgriChart from '../../components/agri-details/agri-chart';

const AgriListDetail = (props) => {
  const agriDetail = props.object;
  
  useEffect(() => {
    document.body.style.cssText = `position: fixed; top: -${window.scrollY}px`
  return () => {
    const scrollY = document.body.style.top
    document.body.style.cssText = `position: ""; top: "";`
    window.scrollTo(0, parseInt(scrollY || '0') * -1)
  }
  }, [])


  return (
        <div id="myModal" className={styles['agri-body']} >
            <div className={styles['agri-details']}>
                <AgriDetail object={agriDetail} className={styles['agri-detail']} />
                <AgriLocation object={agriDetail} className={styles['agri-location']} />
                <AgriChart object={agriDetail} className={styles['agri-chart']} /> 
            </div>
        </div>
  )
}

export default AgriListDetail