import Footer from "../components/footer/Footer";
import Header from "../components/header/Header";

//Swiper
import { Swiper, SwiperSlide } from 'swiper/react';
//import {EffectFade} from "swiper/modules";

import { useState } from "react";
import Autor from "../components/autor/Autor";


export function Home(){

    const [imgCarousel] = useState([
        {id: 1, image: "https://imageio.forbes.com/specials-images/imageserve/66cc9d9ffc537cb99b7a3440/0x0.jpg?format=jpg&crop=1884,1256,x0,y0,safe&height=900&width=1600&fit=bounds"},
        {id: 2, image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPUmz2Jq6irNx1qhilTNPaVOi7kVRl-49xVQ&s"},
        {id: 3, image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-MSHDeRRp2m86wMKYkqx88spBZvID-b4g2A&s"},
    ])


    return (
        <>
            <div className="min-h-screen flex flex-col">
            <Header/>
                <main className="flex-grow">
                    {/* conteudo */}
                    <Swiper className="w-full h-120"
                        effect="fade"
                        //modules={[EffectFade]}
                        pagination={{clickable: true}}
                        navigation
                    >
                        {imgCarousel.map((item)=> (
                            <SwiperSlide key={item.id} className="flex justify-center align-center relative">
                                <img 
                                    src={item.image} alt="Slider" className="w-full"
                                />
                            </SwiperSlide>
                        ))}
                    </Swiper>
                    <Autor/>

                </main>
                <Footer/>
            </div>
           
        </>

    );
}