import { useAtom } from 'jotai'
import React from 'react'
import AgriMap from '../../components/agri-map/AgriMap'
import AgriInfo from '../../components/agri-map/AgriInfo';
import curLocationAtom from '../../atoms/curLocationAtom'
import styles from '../../styles/agri-map-index.module.css';

const index = () => {
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);

  return (
    <div className={styles.frame}>
      {curLocation === '' ? <AgriMap /> :
                            <>
                              <AgriMap /> 
                              <AgriInfo />
                            </>}
    </div> 
  )
}

export default index