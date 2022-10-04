import React, { useEffect } from 'react'
import properties from '../../public/classifiedSVG/overall_svg_settings.json'
import coordinates from '../../public/classifiedSVG/overall_coordinate.json';
import { useAtom } from 'jotai';
import curLocationAtom from '../../atoms/curLocationAtom';
import curCitiesOnProductAtom from '../../atoms/curCitiesOnProductAtom';
import styles from '../../styles/agri-spot.module.css';
import curProductAtom from '../../atoms/curProductAtom';

const AgriSpot = (props) => {
  const list = [];
  const [curLocation, setCurLocation] = useAtom(curLocationAtom)
  const [curProduct, setCurProduct] = useAtom(curProductAtom);
  const [curCitiesOnProduct, setCurCitiesOnProduct] = useAtom(curCitiesOnProductAtom);
  if (curProduct) {
    for (let city of curCitiesOnProduct[curProduct]) {
      list.push(coordinates[curLocation][city]);
    }
  }
  console.log(list);

  return (
    <svg viewBox={properties[curLocation]['viewBox']} xmlns="http://www.w3.org/2000/svg" width="calc(54% - 4rem)"  className={`${styles.position} ${styles[`position-${curLocation}`]}`}>
      <g fill="white" stroke="green" stroke-width="5" transform={properties[curLocation]['transform']}>
        {list.map(item => {
          return curLocation === 'jeonbuk' ? <circle cx={item[0]} cy={item[1]} r='30' /> : <image href='/images/사과.png' x={item[0]} y={item[1]} width='50px' height='50px' />
        })}
      </g>
    </svg>
  )
}

export default AgriSpot