cmake_minimum_required(VERSION 3.0.0)

# Add needed header files
set(systems_demo_INCLUDES
	../../PluginConfig.h
	../../Kernel.h
	demo_config.h
)

# Add all src files
set(systems_demo_LIB
	demo.cpp
)

add_library(systems_demo MODULE ${systems_demo_LIB} ${systems_demo_INCLUDES})
install(TARGETS systems_demo DESTINATION ${CMAKE_INSTALL_PREFIX}/lib)
