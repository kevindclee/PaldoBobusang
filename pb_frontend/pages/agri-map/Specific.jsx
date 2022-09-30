import {React} from 'react'
import { ReactSVG } from 'react-svg';
import {css} from 'glamor'

const Specific = (props) => {
  const loc = `/classifiedSVG/${props.loc}_classified.svg`;
  const styles = css({
    ' svg': {
      width: '1000px',
      height: '700px',
      margin: '0 200px'
    }
  })

  return (
    <ReactSVG src={loc} className={props.loc} {...styles} />
    )
}

export default Specific