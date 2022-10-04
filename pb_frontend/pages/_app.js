import Head from 'next/head';
import {useAtom} from 'jotai';
import '../styles/globals.css'
import Nav from '../components/Nav';
import curAppAtom from '../atoms/curAppAtom';

function MyApp({ Component, pageProps }) {
  const [curApp, setCurApp] = useAtom(curAppAtom);
  return (
    <>
      <Head>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Gugi&family=Sonsie+One&display=swap" rel="stylesheet" />
        <title>PB:PaldoBobusang</title>
      </Head>
      {curApp !== '' && <Nav />}
      <Component {...pageProps} />
    </>
  )
}

export default MyApp
