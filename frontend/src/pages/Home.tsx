import Footer from "../components/footer/Footer";
import Header from "../components/header/Header";
import { Swiper, SwiperSlide } from 'swiper/react';
import {EffectFade} from "swiper/modules";
import { useState } from "react";


export function Home(){

    const [imgCarousel] = useState([
        {id: 1, image: "https://img.freepik.com/vetores-gratis/pilha-de-design-plano-desenhado-a-mao-de-ilustracao-de-livros_23-2149341898.jpg?semt=ais_hybrid&w=740"},
        {id: 2, image: ""},
        {id: 3, image: ""},
        {id: 4, image: ""}

    ])


    return (
        <>
            <div className="min-h-screen flex flex-col">
            <Header/>
                <main className="flex-grow">
                    {/* conteudo */}
                    <Swiper 
                        effect="fade"
                        modules={[EffectFade]}
                        pagination={{clickable: true}}
                        navigation
                    >
                        {imgCarousel.map((item)=> (
                            <SwiperSlide key={item.id}>
                                <img 
                                    src={item.image} alt="Slider" className="slide-item"
                                />
                            </SwiperSlide>
                        ))}
                    </Swiper>
                    

                </main>
                <Footer/>
            </div>
           
        </>

    );
}