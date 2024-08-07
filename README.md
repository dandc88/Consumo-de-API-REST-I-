## Proyecto basado en Apoyo Desafío evaluado - Consumo de API REST (I)

En la carpeta "remote" de data esta la interfaz para el manejo de la api "https://api.openweathermap.org/data/2.5/" con la clave 000477936bfcd7a4b6c887a3a149a0a5.

Como en este punto no se estaba evaluando el uso de Room para guardar los datos que se obtienen con Retrofit, se pasan los  datos desde el HomeFragment al DetalsFragment a traves de bundle con un array. Se creó la funcion populateWeatherData(data: Array<String>) para mostrar los datos. 

En el viewModel se crea la función  getFormattedWeatherData para dar manejar el formato (hacer las conversiones)  de las temperaturas a mostrar dependiendo si se requieren en e Metrics a Imperial. 


Al salir de settings las temperaturas se muestran en el formato seleccionado. 

Se manejan los diferentes estados cuando se piden los datos :

 when (state) {
                        is UIState.Loading -> binding.rvWeather.show()
                        is UIState.Success -> {
                            binding.rvWeather.show()
                            val list = listOf(state.data as WeatherDto)
                            populateRecyclerView(list)
                        }
                        is UIState.Error -> {
                            binding.rvWeather.hide()
                            binding.cityName.hide()
                        }
                    }


                    
