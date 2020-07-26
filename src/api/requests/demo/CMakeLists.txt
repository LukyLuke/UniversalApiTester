cmake_minimum_required(VERSION 3.0.0)

# Add needed header files
set(plugin_requests_demo_INCLUDES
	
)

# Add all src files
set(plugin_requests_demo_LIB
	
)

add_library(plugin_requests_demo SHARED ${plugin_systems_demo_LIB} ${plugin_systems_demo_INCLUDES})
install(TARGETS plugin_requests_demo DESTINATION ${CMAKE_INSTALL_PREFIX}/lib)
