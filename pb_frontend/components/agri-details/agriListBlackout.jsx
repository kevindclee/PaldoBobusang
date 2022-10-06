import styles from '/styles/agriList.module.css';

const AgriListBlackout = ({setIsModal})=> {
    return(
        <>
            <div className={styles['blackout-style']} onClick={() => {setIsModal(false)}} />
        </>
    )
}

export default AgriListBlackout