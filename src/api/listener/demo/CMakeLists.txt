cmake_minimum_required(VERSION 3.0.0)

# Add needed header files
set(plugin_listener_demo_INCLUDES
	
)

# Add all src files
set(plugin_listener_demo_LIB
	
)

add_library(plugin_listener_demo SHARED ${plugin_systems_demo_LIB} ${plugin_systems_demo_INCLUDES})
install(TARGETS plugin_listener_demo DESTINATION ${CMAKE_INSTALL_PREFIX}/lib)
