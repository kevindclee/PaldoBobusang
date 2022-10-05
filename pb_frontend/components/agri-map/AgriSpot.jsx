import React, { useEffect } from 'react'
import properties from '../../public/classifiedSVG/overall_svg_settings.json'
import coordinates from '../../public/classifiedSVG/overall_coordinate.json';
import { useAtom } from 'jotai';
import curLocationAtom from '../../atoms/curLocationAtom';
import curCitiesOnProductAtom from '../../atoms/curCitiesOnProductAtom';
import styles from '../../styles/agri-spot.module.css';
import curProductAtom from '../../atoms/curProductAtom';
import curProductListAtom from '../../atoms/curProductList';

const AgriSpot = (props) => {
  const list = [];
  const [curLocation, setCurLocation] = useAtom(curLocationAtom)
  const [curProduct, setCurProduct] = useAtom(curProductAtom);
  const [curProductList, setCurProductList] = useAtom(curProductListAtom);
  const [curCitiesOnProduct, setCurCitiesOnProduct] = useAtom(curCitiesOnProductAtom);
  if (curCitiesOnProduct && curProduct) {
    for (let city of curCitiesOnProduct) {
      if (city.localEngName === curLocation) {
        // console.log(coordinates[curLocation][city.cityEngName]);
        list.push(coordinates[curLocation][city.cityEngName]);
      }
    }
  }
  let productImage;
  if (curProductList) {
    for (let product of curProductList) {
      if (product.name === curProduct) productImage = product.img;
    }
  }
  console.log("productImage: " + productImage);

  return (
    <svg viewBox={properties[curLocation]['viewBox']} xmlns="http://www.w3.org/2000/svg" width="calc(54% - 4rem)"  className={`${styles.position} ${styles[`position-${curLocation}`]}`}>
      <g fill="white" stroke="green" stroke-width="5" transform={properties[curLocation]['transform']}>
        {curCitiesOnProduct? list.map(item => {
          return curLocation === 'jeonbuk' ? <circle cx={item[0]} cy={item[1]} r='30' /> : <image href={productImage} x={item[0]} y={item[1]} width='2.5rem' height='2.5rem' />
        }) : <></>}
      </g>
    </svg>
  )
}

export default AgriSpot