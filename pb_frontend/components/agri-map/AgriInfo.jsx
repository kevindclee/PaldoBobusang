import React, { isValidElement, useEffect } from 'react'
import styles from '../../styles/agri-info.module.css';
import { useState } from 'react';
import { useAtom } from 'jotai';
import curLocationAtom from '../../atoms/curLocationAtom';
import LocationTable from './LocationTable';
import curProductAtom from '../../atoms/curProductAtom';

const AgriInfo = () => {
  const [curCategory, setCurCategory] = useState(null);
  const [isVegitable, setIsVegitable] = useState(false); 
  const [isFruit, setIsFruit] = useState(false); 
  const [curProduct, setCurProduct] = useAtom(curProductAtom); 
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);
  useEffect(() => {
    if (curCategory === 'vegitable') {
      setIsVegitable(true); setIsFruit(false); 
    } else if (curCategory === 'fruit') {
      setIsVegitable(false); setIsFruit(true);
    }
  }, [curCategory, isFruit, isVegitable, curProduct]);
  const products = {
    'vegitable': ['양파', '고구마', '쌀', '마늘'],
    'fruit': ['사과', '딸기', '배', '포도'],
  }

  const citiesOnProduct = {
    '양파': ['Goseong', 'Samcheok', 'Yangyang', 'Hwacheon', 'Chuncheon'],
    '고구마': ['Pyeongchang', 'Taebaek', 'Yeongwol', 'Cheorwon', 'Wonju'],
  }
  
  const clickHandler = event => {
                        setCurCategory(event.target.id);
                        setCurProduct(null);
                      }
  const selectHandler = event => {
    setCurProduct(event.target.textContent);
    const items = document.getElementsByClassName('item');
    for (let item of items) item.style = 'background-color: rgba(255, 234, 167, 1);';
    event.target.style = 'border-radius: 1rem 1rem 0 0; background-color: rgba(211, 214, 218, 0.5);'
  }
  const mouseOverHandler = event => event.target.style
                                     = 'background-color: rgba(211, 214, 218, 0.5);'
  const mouseOutHandler = event => {
   if (event.target.id === curProduct){
     event.target.style = 'border-radius: 1rem 1rem 0 0; background-color: rgba(211, 214, 218, 0.5);';
   } else {
     event.target.style = 'background-color: rgba(255, 234, 167, 1);';
   }
  }

  return (
    <div className={styles['agri-info']}>
      <div className={styles.categories}>
        <div className={styles[`category-${isVegitable}`]} id='vegitable' onClick={clickHandler}>야채</div>
        <div className={styles[`category-${isFruit}`]} id='fruit' onClick={clickHandler}>과일</div>
      </div>
      {curCategory ? <div className={styles['products-true']} id='products'>
                      {products[curCategory].map((item, index) => <>
                                                                    <div onClick={selectHandler} 
                                                                        onMouseOver={mouseOverHandler}
                                                                        onMouseOut={mouseOutHandler}
                                                                        className={`item ${styles.item} ${index}`}
                                                                        id={item} key={index}>{item}
                                                                    </div>
                                                                    {curProduct === item? <LocationTable 
                                                                                            array={citiesOnProduct[item]}
                                                                                            local={curLocation} /> : <></>}
                                                                  </>
                                                                  )}
                     </div> : 
                     <div className={styles['products-false']}></div>}
      
    </div>
  )
}

export default AgriInfo