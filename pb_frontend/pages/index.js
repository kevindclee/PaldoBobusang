import { useAtom } from "jotai";
import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import curAppAtom from "../atoms/CurAppAtom";
import styles from "../styles/main.module.css";
import mobile from "../styles/mobile/main.module.css";
import { useMediaQuery } from "react-responsive";

export default function Home() {
  const isLaptop = useMediaQuery({ minDeviceWidth: 1224 });
  const [curApp, setCurApp] = useAtom(curAppAtom);
  const [curView, setCurView] = useState([false, false]);
  const router = useRouter();
  useEffect(() => setCurApp(""), []);

  const mouseOverHandler = (event) => {
    const view = event.target.id;
    view === "agri-list"
      ? setCurView([true, false])
      : setCurView([false, true]);
  };

  const fingerClickHandler = (event) => {
    const view = event.target.id;
    view === "agri-list"
      ? setCurView([true, false])
      : setCurView([false, true]);
  };

  const listClickHandler = (event) => {
    router.push("agri-list");
  };

  const mapClickHandler = (event) => {
    router.push("agri-map");
  };

  return isLaptop ? (
    <div className={styles.body}>
      <div className={styles.main}>
        <div className={styles.logo}>
          <div className={styles.back}></div>
          <p className={styles["logo-desc"]}>제철 농산물 정보 제공 플랫폼</p>
          <h1 className={styles["ko-logo"]}>팔도 보부상</h1>
          <h2 className={styles["en-logo"]}>Paldo Bobusang</h2>
        </div>
        <div className={styles.apps}>
          <div
            className={styles.button}
            id="agri-list"
            onMouseOver={mouseOverHandler}
            onClick={listClickHandler}>
            보부상 봇짐
          </div>
          <div
            className={styles.button}
            id="agri-map"
            onMouseOver={mouseOverHandler}
            onClick={mapClickHandler}>
            보부상 지도
          </div>
        </div>
        <div className={styles["practice-video-frame"]}>
          <div className={styles["frontier"]}></div>
          {curView[0] ? (
            <div className={styles["video-frame"]}>
              <img
                className={styles["video"]}
                src="/images/video-agri-list.gif"
              />
            </div>
          ) : (
            <div
              className={styles["question-mark"]}
              id="agri-list"
              onMouseOver={mouseOverHandler}>
              ?
            </div>
          )}
          {curView[1] ? (
            <div className={styles["video-frame"]}>
              <img
                className={styles["video"]}
                src="/images/video-agri-map.gif"
              />
            </div>
          ) : (
            <div
              className={styles["question-mark"]}
              id="agri-map"
              onMouseOver={mouseOverHandler}>
              ?
            </div>
          )}
        </div>
      </div>
    </div>
  ) : (
    <div className={mobile.body}>
      <div className={mobile.main}>
        <div className={mobile.logo}>
          <div className={mobile.back}></div>
          <p className={mobile["logo-desc"]}>제철 농산물 정보 제공 플랫폼</p>
          <h1 className={mobile["ko-logo"]}>팔도 보부상</h1>
          <h2 className={mobile["en-logo"]}>Paldo Bobusang</h2>
        </div>
        <div className={mobile.apps}>
          <div
            className={mobile.button}
            id="agri-list"
            onMouseOver={mouseOverHandler}
            onClick={listClickHandler}>
            보부상 봇짐
          </div>
          <div
            className={mobile.button}
            id="agri-map"
            onMouseOver={mouseOverHandler}
            onClick={mapClickHandler}>
            보부상 지도
          </div>
        </div>
        <div className={mobile["practice-video-frame"]}>
          {curView[0] ? (
            <div className={mobile["video-frame"]}>
              <img
                className={mobile["video"]}
                src="/images/video-agri-list.gif"
              />
            </div>
          ) : (
            <div
              className={mobile["question-mark-left"]}
              id="agri-list"
              onClick={fingerClickHandler}>
              ?
            </div>
          )}
          {curView[1] ? (
            <div className={mobile["video-frame"]}>
              <img
                className={mobile["video"]}
                src="/images/video-agri-map.gif"
              />
            </div>
          ) : (
            <div
              className={mobile["question-mark-right"]}
              id="agri-map"
              onClick={fingerClickHandler}>
              ?
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
