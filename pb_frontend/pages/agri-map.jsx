import React from 'react'
import { useAtom } from 'jotai'
import curAppAtom from '../atoms/curAppAtom'

const ArgiMap = () => {
  const [curApp, setCurApp] = useAtom(curAppAtom);
  setCurApp('agri-map');

  return (
    <div>ArgiMap</div>
  )
}

export default ArgiMap