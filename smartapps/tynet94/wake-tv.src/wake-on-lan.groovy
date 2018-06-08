/**
 *  Computer Power Control with Wake on LAN
 *
 *  Copyright 2016 Matt Sutton
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Wake TV via network",
    namespace: "tynet94",
    author: "Matt Sutton, Ty Alexander",
    description: "Wake a TV .",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {    
    section("Wake Button") {
    	input "wakeButton", "capability.momentary", required: true, title: "Button"
    }
    
    section("TV Network Settings") {
        input "mac", "text", required: true, title: "TV MAC Address"
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(theswitch, "switch.on", theswitchOnHandler)
}

def wakeSwitchOnHandler(evt) {
	log.debug "wakeSwitchOnHandler: Running"
    sendHubCommand(wakeMac(evt))
	wakeswitch.off()
}

def wakeMac(evt) {
    def result = new physicalgraph.device.HubAction (
    	"wake on lan $mac",
        physicalgraph.device.Protocol.LAN,
        null
    )    
    return result
}
