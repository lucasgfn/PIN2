import Footer from "../../components/footer/Footer";
import Header from "../../components/header/Header";

//Swiper
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/effect-fade';

import { Navigation, Pagination, EffectFade } from 'swiper/modules';

//import {EffectFade} from "swiper/modules";

import { useState } from "react";
import { Container } from "./section/Container";


export function Home(){

    const [imgCarousel] = useState([
      {
        id: 1,
        image:
          "https://imageio.forbes.com/specials-images/imageserve/66cc9d9ffc537cb99b7a3440/0x0.jpg?format=jpg&crop=1884,1256,x0,y0,safe&height=900&width=1600&fit=bounds",
      },
      {
        id: 2,
        image:
          "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=1600&q=80",
      },
      {
        id: 4,
        image:
          "https://images.unsplash.com/photo-1507842217343-583bb7270b66?auto=format&fit=crop&w=1600&q=80",
      },
      {
        id: 5,
        image:
          "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=1600&q=80",
      },
    ]);
    

    return (
        <>
            <div className="min-h-screen flex flex-col">
            <Header/>
                <main className="flex-grow">
                    {/* conteudo */}
                    <Swiper 
                        className="w-full h-120"
                        modules={[Navigation, Pagination, EffectFade]}
                        effect="fade"
                        pagination={{ clickable: true }}
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
                    <Container/>
                    
                </main>
                <Footer/>
            </div>
           
        </>

    );
}