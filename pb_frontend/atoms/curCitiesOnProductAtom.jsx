import { atom } from "jotai";

const curCitiesOnProductAtom = atom({
  '양파': ['Goseong', 'Samcheok', 'Yangyang', 'Hwacheon', 'Chuncheon'],
  '고구마': ['Pyeongchang', 'Taebaek', 'Yeongwol', 'Cheorwon', 'Wonju'],
});

export default curCitiesOnProductAtom