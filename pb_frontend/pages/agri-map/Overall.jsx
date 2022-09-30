import React from 'react'
import Map from '/classifiedSVG/overall_classified.svg';

const Overall = props => {
  const size = props.size;
  const clickHandler = event => {
    const location = event.target.classList[0];
    props.clickHandler(location);
    document.getElementsByTagName('svg')[0].style='position: absolute'
  }

  return (
    size !== 'small' ? <Map onClick={clickHandler} className={`Overall-${size}`} /> :
                       <Map onClick={clickHandler} className={`Overall-${size}`} width='300px' height='300px' />
  )
}

export default Overall