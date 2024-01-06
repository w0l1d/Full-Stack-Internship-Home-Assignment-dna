import '@/styles/globals.css'
import Layout from "@/components/RootLayout";

export default function App({ Component, pageProps }) {
  return <Layout><Component {...pageProps} /></Layout>
}
