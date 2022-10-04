import { useAtom } from 'jotai'
import { useState } from 'react';
import { useRouter } from 'next/router'
import curAppAtom from '../atoms/curAppAtom';
import styles from '../styles/main.module.css';

export default function Home() {
  const [curApp, setCurApp] = useAtom(curAppAtom);
  const [curView, setCurView] = useState('');
  const router = useRouter();
  setCurApp('');

  const mouseOverHandler = event => {
    const view = event.target.id;
    setCurView(view);
  };

  const listClickHandler = event => {
    router.push(`/${curView}`);
  };

  const mapClickHandler = event => {
    router.push(`/${curView}`);
  };

  return (
    <div className={styles.body}>
      <div className={styles.main}>
      <div className={styles.logo}>
          <div className={styles.back}></div>
          <p className={styles['logo-desc']}>제철 농산물 정보 제공 플랫폼</p>
          <h1 className={styles['ko-logo']}>팔도 보부상</h1>
          <h2 className={styles['en-logo']}>Paldo Bobusang</h2>
        </div>
        <div className={styles.apps}>
          <div className={styles.attention}>
            <div className={`${styles['attention-arrow']} ${styles.left}`}></div>
            <div className={styles['attention-content']}>
              마우스를 올리시면<br/>영상이 재생됩니다
            </div>
            <div className={`${styles['attention-arrow']} ${styles.right}`}></div>
          </div>
          <div className={curView === 'agri-list' ? `${styles.button} ${styles[curView]}` : styles.button} id='agri-list' onMouseOver={mouseOverHandler} onClick={listClickHandler} >농산물 목록</div>
          <div className={curView === 'agri-map' ? `${styles.button} ${styles[curView]}` : styles.button} id='agri-map' onMouseOver={mouseOverHandler} onClick={mapClickHandler} >농산물 지도</div>
        </div>
        { curView !== '' && <div className={`${styles["service-video"]}`}>{curView}</div>}
      </div>
    </div>
  )
}
