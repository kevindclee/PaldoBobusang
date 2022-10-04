import React from 'react';
import styles from '../../styles/agri-detail.module.css';
import { useState } from 'react';

const AgriDetail = (props) => {
  const [kind, setKind] = useState(false);
  const mouseOverHandler = event => setKind(true);
  const list = ['아오리', '홍로', '부사']

  return (
    <div className={`${props.className} ${styles['detail-body']}`}>
      <div className={styles['detail-table']}>?</div>
    </div>
  )
}

export default AgriDetail