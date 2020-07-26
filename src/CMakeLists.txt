cmake_minimum_required(VERSION 3.0.0)

# Find includes in corresponding build directories
set(CMAKE_INCLUDE_CURRENT_DIR ON)

set(CMAKE_BUILD_DIR ${PROJECT_SOURCE_DIR}/build)

# Add all src files
set(universalapitester_SRC
	main.cpp
	common/logger.cpp
	common/Plugin.cpp
	app/Application.cpp
)

# Add needed header files
set(universalapitester_INCLUDES
	api/PluginConfig.h
	common/SharedLibraryLoader.h
	common/Plugin.h
	api/Kernel.h
	app/Application.h
)

if(EXISTS "${CMAKE_SOURCE_DIR}/.git")
	execute_process(
		COMMAND git rev-parse HEAD
		WORKING_DIRECTORY ${CMAKE_SOURCE_DIR}
		OUTPUT_VARIABLE GIT_BRANCH
		OUTPUT_STRIP_TRAILING_WHITESPACE
	)
else()
	set(GIT_BRANCH "Unknown")
endif()
add_definitions(-DGIT_BRANCH=${GIT_BRANCH})

# Set build dir for your executable
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_BUILD_DIR}/bin)
set(LIBRARY_OUTPUT_PATH ${CMAKE_BUILD_DIR}/lib)
message( STATUS "EXECUTABLE_OUTPUT_PATH: " ${EXECUTABLE_OUTPUT_PATH} )
message( STATUS "LIBRARY_OUTPUT_PATH: " ${LIBRARY_OUTPUT_PATH} )

file(MAKE_DIRECTORY ${EXECUTABLE_OUTPUT_PATH})
file(MAKE_DIRECTORY ${LIBRARY_OUTPUT_PATH})

# Create an executable
add_executable(universalapitester ${universalapitester_SRC})

# External libs
target_link_libraries(universalapitester ${Boost_LIBRARIES} ${CMAKE_DL_LIBS})

# Install the executable
install(TARGETS universalapitester DESTINATION ${CMAKE_INSTALL_PREFIX}/bin)

add_subdirectory(api)
