import { useRouter } from "next/router";
import React from "react";
import styles from "../styles/nav.module.css";
import mobile from "../styles/mobile/nav.module.css";
import curLocationAtom from "../atoms/CurLocationAtom";
import { useAtom } from "jotai";
import { useMediaQuery } from "react-responsive";

const Nav = () => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const router = useRouter();
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);

  const clickHandler = (event) => {
    const id = event.target.id;
    id === "home" ? router.push("/") : router.push(`/${id}`);
    setCurLocation("");
  };

  const mouseOverHandler = (event) => {
    const className = event.target.classList[0];
    document.getElementsByClassName(className)[1].style.display = "block";
  };

  const mouseOutHandler = (event) => {
    const className = event.target.classList[0];
    document.getElementsByClassName(className)[1].style.display = "none";
  };

  return isLaptop ? (
    <div className={styles["nav-frame"]}>
      <div className={styles["logo"]} id="home" onClick={clickHandler}>
        <div className={styles["logo-background"]}></div>
        <div className={styles["logo-desc"]}>제철 농산물 플랫폼</div>
        <div className={styles["ko-logo"]}>팔도 보부상</div>
        <div className={styles["en-logo"]}>PaldoBobusang</div>
      </div>
      <div className={styles["menus"]}>
        <div
          className={`home ${styles["menu"]}`}
          id="home"
          onClick={clickHandler}
          onMouseOver={mouseOverHandler}
          onMouseOut={mouseOutHandler}>
          홈
        </div>
        <div
          className={`agri-list ${styles["menu"]}`}
          id="agri-list"
          onClick={clickHandler}
          onMouseOver={mouseOverHandler}
          onMouseOut={mouseOutHandler}>
          보부상 봇짐
        </div>
        <div
          className={`agri-map ${styles["menu"]}`}
          id="agri-map"
          onClick={clickHandler}
          onMouseOver={mouseOverHandler}
          onMouseOut={mouseOutHandler}>
          보부상 지도
        </div>
        <div className={`home ${styles["home"]} ${styles["menu-spot"]}`}></div>
        <div
          className={`agri-list ${styles["agri-list"]} ${styles["menu-spot"]}`}></div>
        <div
          className={`agri-map ${styles["agri-map"]} ${styles["menu-spot"]}`}></div>
      </div>
    </div>
  ) : (
    <div className={mobile["nav-frame"]}>
      <div className={mobile["logo"]} id="home" onClick={clickHandler}>
        <div className={mobile["logo-background"]}></div>
        <div className={mobile["logo-desc"]}>제철 농산물 플랫폼</div>
        <div className={mobile["ko-logo"]}>팔도 보부상</div>
        <div className={mobile["en-logo"]}>PaldoBobusang</div>
      </div>
      <div className={mobile["menus"]}>
        <div
          className={`home ${mobile["menu"]}`}
          id="home"
          onClick={clickHandler}
          onMouseOver={mouseOverHandler}
          onMouseOut={mouseOutHandler}>
          홈
        </div>
        <div
          className={`agri-list ${mobile["menu"]}`}
          id="agri-list"
          onClick={clickHandler}
          onMouseOver={mouseOverHandler}
          onMouseOut={mouseOutHandler}>
          보부상 봇짐
        </div>
        <div
          className={`agri-map ${mobile["menu"]}`}
          id="agri-map"
          onClick={clickHandler}
          onMouseOver={mouseOverHandler}
          onMouseOut={mouseOutHandler}>
          보부상 지도
        </div>
        <div className={`home ${mobile["home"]} ${mobile["menu-spot"]}`}></div>
        <div
          className={`agri-list ${mobile["agri-list"]} ${mobile["menu-spot"]}`}></div>
        <div
          className={`agri-map ${mobile["agri-map"]} ${mobile["menu-spot"]}`}></div>
      </div>
    </div>
  );
};

export default Nav;
