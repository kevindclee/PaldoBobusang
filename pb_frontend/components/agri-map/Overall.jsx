import { useAtom } from "jotai";
import React from "react";
import { useMediaQuery } from "react-responsive";
import curLocationAtom from "../../atoms/CurLocationAtom";
import curProductListAtom from "../../atoms/CurProductList";
import Map from "../../public/classifiedSVG/overall_classified.svg";
import MobileMap from "../../public/classifiedSVG/mobile/overall_classified.svg";

const Overall = (props) => {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const [curLocation, setCurLocation] = useAtom(curLocationAtom);
  const [curProductList, setCurProductList] = useAtom(curProductListAtom);
  const className = "overall_classified_svg__" + curLocation;
  const size = props.size;
  const clickHandler = (event) => {
    const location = event.target.classList[0];
    if (
      location !== "Overall-large" &&
      location !== "Overall-small" &&
      location !== "overall_classified_svg__TEXT"
    ) {
      props.clickHandler(location);
      setCurProductList(null);
    }
  };

  const mouseOverHandler = (event) => {
    const map = document.getElementsByClassName("Overall-small")[0];
    map ? (map.style.transform = "scale(1.3)") : <></>;
    const origin = document.getElementsByClassName(className);
    if (
      event.target.localName === "path" &&
      event.target.classList[0] !== className
    ) {
      const spots = document.getElementsByClassName(event.target.classList[0]);
      event.target.style.cursor = "pointer";
      for (let spot of spots) spot.style.fill = "#fdcb6e";
    }
  };

  const mouseOutHandler = (event) => {
    const map = document.getElementsByClassName("Overall-small")[0];
    map ? (map.style.transform = "scale(1)") : <></>;
    const origin = document.getElementsByClassName(className);
    if (
      event.target.localName === "path" &&
      event.target.classList[0] !== className
    ) {
      const spots = document.getElementsByClassName(event.target.classList[0]);
      for (let spot of spots) spot.style.fill = "#b2df8a";
    }
  };

  return isLaptop ? (
    size !== "small" ? (
      <Map
        onClick={clickHandler}
        className={`Overall-${size}`}
        width="100%"
        height="100%"
        onMouseOver={mouseOverHandler}
        onMouseOut={mouseOutHandler}
        style={{ position: "inherit" }}
      />
    ) : (
      <Map
        onClick={clickHandler}
        className={`Overall-${size}`}
        width="15%"
        height="15%"
        onMouseOver={mouseOverHandler}
        onMouseOut={mouseOutHandler}
        style={{ position: "absolute", "z-index": "3" }}
      />
    )
  ) : size !== "small" ? (
    <MobileMap
      onClick={clickHandler}
      className={`Overall-${size}`}
      width="100%"
      height="100%"
      onMouseOver={mouseOverHandler}
      onMouseOut={mouseOutHandler}
      style={{ position: "inherit" }}
    />
  ) : (
    <MobileMap
      onClick={clickHandler}
      className={`Overall-${size}`}
      width="20%"
      height="35%"
      style={{ position: "absolute", "z-index": "3", left: "5vw", top: "0" }}
    />
  );
};
export default Overall;
