/*
 * Macros to be used in Plugins for convinience.
 * 
 * Copyright (C) 2020  Lukas zurschmiede <lukas.zurschmiede@ranta.ch>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef UAPI_TESTER_PLUGIN_CONFIG_H
#define UAPI_TESTER_PLUGIN_CONFIG_H

// Decides whether symbols are imported statically/dynamically
// from a so/dll by the main application or exported as an so/dll by the plugins.
// The UAPI_TESTER_SOURCE symbol is defined by the source files of the plugins.
#if defined(UAPI_TESTER_STATICLIB)
	#define UAPI_TESTER_API
#else
	#define UAPI_TESTER_API __attribute__ ((visibility ("default")))
#endif

#endif // UAPI_TESTER_PLUGIN_CONFIG_H
