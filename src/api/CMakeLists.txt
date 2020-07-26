cmake_minimum_required(VERSION 3.0.0)

# Add needed header files
set(universalapitester_INCLUDES
	ListenerPlugin.h
	RequestsPlugin.h
	SystemPlugin.h
)

# Add all src files
set(universalapitester_SRC
	
)

add_subdirectory(listener)
add_subdirectory(requests)
add_subdirectory(systems)
