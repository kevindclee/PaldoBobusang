import React, { useEffect } from "react";
import styles from "../../styles/agri-info.module.css";
import mobile from "../../styles/mobile/agri-info.module.css";
import { useState } from "react";
import { useAtom } from "jotai";
import curLocationAtom from "../../atoms/CurLocationAtom";
import LocationTable from "./LocationTable";
import curProductAtom from "../../atoms/CurProductAtom";
import curCitiesOnProductAtom from "../../atoms/CurCitiesOnProductAtom";
import curProductListAtom from "../../atoms/CurProductList";
import constant from "../../public/constant.json";
import { useMediaQuery } from "react-responsive";

const AgriInfo = () => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const [curCategory, setCurCategory] = useState(null);
  const [isCategory, setIsCategory] = useState([false, false]);
  const [curList, setCurList] = useState(null);
  const [curProductList, setCurProductList] = useAtom(curProductListAtom);
  const [curProduct, setCurProduct] = useAtom(curProductAtom);
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);
  const [curCitiesOnProduct, setCurCitiesOnProduct] = useAtom(
    curCitiesOnProductAtom
  );

  useEffect(() => {
    if (curCategory && !curProductList) {
      setIsCategory([false, false]);
      fetch(`${constant.backend_url}/AgriMap/${curLocation}/${curCategory}`)
        .then((response) => response.json())
        .then((data) => {
          const curList = [];
          data.map((item) => curList.push({ ...item }));
          setCurProductList(curList);
        })
        .catch((error) => console.error(error));
      curCategory === "vegetable"
        ? setIsCategory([true, false])
        : setIsCategory([false, true]);
    }

    if (curProduct && !curCitiesOnProduct) {
      fetch(
        `${constant.backend_url}/${curCategory}s/itemNameAndLocalEngName/${curProduct}/${curLocation}`
      )
        .then((response) => response.json())
        .then((data) => {
          const curList = [];
          data.locations.map((item) => curList.push(item));
          setCurCitiesOnProduct(curList);
        })
        .catch((error) => console.error(error));
    }
  }, [curCategory, curProduct]);

  const clickHandler = (event) => {
    setCurCategory(event.target.id);
    setCurProduct(null);
    setCurProductList(null);
    setCurCitiesOnProduct(null);
  };
  const selectHandler = (event) => {
    setCurProduct(event.target.textContent);
    setCurCitiesOnProduct(null);
    const items = document.getElementsByClassName("item");
    for (let item of items)
      item.style = "background-color: rgba(255, 234, 167, 0.5);";
    event.target.style = "background-color: rgba(211, 214, 218, 0.5);";
  };
  const mouseOverHandler = (event) =>
    (event.target.style = "background-color: rgba(211, 214, 218, 0.5);");
  const mouseOutHandler = (event) => {
    if (event.target.id === curProduct) {
      event.target.style =
        "border-radius: 1rem 1rem 0 0; background-color: rgba(211, 214, 218, 0.5);";
    } else {
      event.target.style = "border: 1px solid rgba(255, 234, 167, 1);";
    }
  };

  return isLaptop ? (
    <div className={styles["agri-info"]}>
      <div className={styles.categories}>
        <div
          className={styles[`category-${isCategory[0]}`]}
          id="vegetable"
          onClick={clickHandler}>
          야채
        </div>
        <div
          className={styles[`category-${isCategory[1]}`]}
          id="fruit"
          onClick={clickHandler}>
          과일
        </div>
      </div>
      {curProductList ? (
        <div className={styles["products-true"]} id="products">
          {curProductList.map((item, index) => (
            <>
              <div
                onClick={selectHandler}
                onMouseOver={mouseOverHandler}
                onMouseOut={mouseOutHandler}
                className={`item ${styles.item}`}
                id={item.itemName}
                key={index}>
                {item.itemName}
              </div>
              {curProduct === item.itemName ? (
                <LocationTable array={curCitiesOnProduct} local={curLocation} />
              ) : (
                <></>
              )}
            </>
          ))}
        </div>
      ) : (
        <div className={styles["products-false"]}></div>
      )}
    </div>
  ) : (
    <div className={mobile["agri-info"]}>
      <div className={mobile.categories}>
        <div
          className={mobile[`category-${isCategory[0]}`]}
          id="vegetable"
          onClick={clickHandler}>
          야채
        </div>
        <div
          className={mobile[`category-${isCategory[1]}`]}
          id="fruit"
          onClick={clickHandler}>
          과일
        </div>
      </div>
      {curProductList ? (
        <div className={mobile["products-true"]} id="products">
          {!curProduct ? (
            <div className={mobile["has-no-cur"]}>
              {curProductList.map((item, index) => (
                <div
                  onClick={selectHandler}
                  onMouseOver={mouseOverHandler}
                  onMouseOut={mouseOutHandler}
                  className={`item ${mobile.item}`}
                  id={item.itemName}
                  key={index}>
                  {item.itemName}
                </div>
              ))}
            </div>
          ) : (
            <div className={mobile["has-cur"]}>
              <div className={mobile["product-list"]}>
                {curProductList.map((item, index) => (
                  <div
                    onClick={selectHandler}
                    onMouseOver={mouseOverHandler}
                    onMouseOut={mouseOutHandler}
                    className={`item ${mobile.item}`}
                    id={item.itemName}
                    key={index}>
                    {item.itemName}
                  </div>
                ))}
              </div>
              <div className={mobile["city-list"]}>
                <LocationTable array={curCitiesOnProduct} local={curLocation} />
              </div>
            </div>
          )}
        </div>
      ) : (
        <div className={mobile["products-false"]}></div>
      )}
    </div>
  );
};

export default AgriInfo;
