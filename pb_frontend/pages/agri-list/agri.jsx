import React, { useState } from 'react'
import AgriListDetail from './agri-list-detail';
import AgriListBlackout from './agriListBlackout';
import styles from '/styles/agri.module.css';

const Agri = (props) => {

    const agri = props.object;

    const img_url = '/images/apple.jpg';

    const [modalVisible, setModalVisible] = useState(false);

    const modalChange = () => {
        setModalVisible(!modalVisible);
    };

    return (
        <>
            <div className={styles['agri']} onClick={modalChange}>
                <div >
                    <div className={styles['agri-frame']}>
                        <img className={styles['picture']} id="modalImage" src={img_url} />
                    </div>
                    <div className={styles['title']}>{agri.itemName}</div>
                </div>
            </div>
            {modalVisible && <AgriListBlackout setIsModal={modalChange}/>}
            {modalVisible && <AgriListDetail object={agri}/>}
        </>
    )
}

export default Agri