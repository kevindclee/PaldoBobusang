import React, {useState, useEffect} from 'react'
import Agri from './agri'
import styles from '/styles/agriList.module.css';
import data from '../jsonData.js'

const AgriList = (props) => {
    const [agriList, setAgriList] = useState([]);

    useEffect(() => {
      fetch('http://localhost:8090/fruits/list/harvest')
      .then(response => response.json())
      .then(data => setAgriList(data))
    },[]);

    console.log(agriList);

    //  const agriList = props.AgriList;
    // const agriList = data;
    // const agriList = [1,2,3,4,5];
    
     const onClickHandler = (id) => {
        props.onClickHandler(id);
      };

      return (
        <div className={styles['agri-list']}>
            {agriList.map(agri => {
              return(
                <Agri  object={agri} onClickHandler={onClickHandler}/>
              )
            })
                
                
               
            }
        </div>
      )
}

export default AgriList