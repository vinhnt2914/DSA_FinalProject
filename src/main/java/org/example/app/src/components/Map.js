// Essential for the map to work
// including react and react-leaflet
import React, { Component } from "react";
// Individual components
import DebugMode from "./DebugMode";
import {
	MapContainer,
	Marker,
	Popup,
	ZoomControl,
	ImageOverlay,
} from "react-leaflet";
import "../App.css";
import { CRS } from "leaflet";
import MapCustom from "./MapCustom";
import MapCustom2 from "./MapCustom2";



/**
 * The Map component.
 * @extends Component
 * @param {Object} props - The props of the component.
 * @param {Object} props.state - The state of the component. Consists of 
 * latitude, longitude, and zoom level.
 * @param {Array} props.state.markers - The markers to be displayed on the map.
 * @returns {JSX.Element} The rendered Map component.
 **/
class Map extends Component {
	constructor(props) {
		super(props);
		this.state = {
			lat: 51.05,
			lng: +0.09,
			zoom: 0.45,
		};
		// this.moveTo = this.moveTo.bind(this);
		this.setCenter = this.setCenterProps.bind(this);
		this.listOfMarkers = [];
		this.PointsOfBound = [];
		this.debugData = this.initDataForDebug();
	}

	// TODO: Implement the debug mode
	initDataForDebug() {
		console.log("Init debug data");
		return {
			lat: this.state.lat,
			lng: this.state.lng,
			zoom: this.state.zoom,
		};
	};



	onBoundDefined(topLeft, topRight, bottomLeft, bottomRight) { }

	componentDidMount() {
		this.initDataForDebug();
	}

	componentDidUpdate(prevProps) {
		if (this.props.state !== prevProps.state) {
			this.setCenterProps(this.props.state.lng, this.props.state.lat, this.props.state.zoom);
			this.PointsOfBound = this.props.PointsOfBound;
			console.log("New bound: ", this.PointsOfBound);
			// if the data also include markers
			// put them on the map
			if (this.props.markers) {
				this.fetchMarkers(this.props.state.markers);

				// calculate the number of markers
				this.debugData.activeNum = this.props.state.markers.length;
			}
		}

	}

	// TODO: Get a list of nearby places 

	setCenterProps(longitude, latitude, zoom) {
		this.setState({
			lat: latitude,
			lng: longitude,
			zoom: zoom,
		});
		console.log("Set center to: ", longitude, latitude, zoom);
	}

	// Set bounds for the map
	setBounds(longtitude, latitude, width, height) { }

	/**
	 * Fetches markers from JSON data and adds them to the map.
	 * @param {Array} JSONData - The JSON data containing marker information.
	 */
	fetchMarkers(JSONData) {
		JSONData.forEach((marker) => {
			// Add a marker to the map for each JSON entry
			// using React-Leaflet's Marker component
			this.listOfMarkers.push(
				<Marker position={[marker.lat, marker.lng]}>
					<Popup>{marker.name}</Popup>
				</Marker>
			);
		});
	}

	/**
	 * Renders the Map component.
	 * @returns {JSX.Element} The rendered Map component.
	 */
	render() {
		// Diagonal start and end position define the area of the map
		const bounds = [[0, 0], [10000000, 10000000]];
		console.log(
			"Current state: ",
			this.state.lat,
			this.state.lng,
			this.state.zoom
		);
		return (
			<>
				{/* Locate the debug button at the bottom right */}
				<div className="absolute bottom-0 right-0 m-8 z-30">
					<DebugMode props={this.debugData} />
					{/* <InfoDiag /> */}
				</div>
				{/* The main map */}
				<MapContainer
					center={[this.state.lat, this.state.lng]}
					zoom={this.state.zoom}
					className="h-dvh z-10"
					bounds={bounds}
					zoomControl={false}
					minZoom={-1000}
					maxZoom={0.45}
					crs={CRS.Simple}
					zoomSnap={0.1}
					zoomDelta={0.1}
				>
					{/* Rectangle bound */}
					<MapCustom2 boundForPolygon={this.PointsOfBound} />
					{/* Map that moves based on the position */}
					<MapCustom lat={this.state.lat} lng={this.state.lng} zoom={this.state.zoom} />
					<ImageOverlay
						url="https://preview.redd.it/ih6no69aj90y.png?auto=webp&s=8e4f9101f58e0812f3625a51ec65c9b7c050da75"
						bounds={bounds}
						className="border box-border border-black"
					/>
					<ZoomControl position="bottomleft" />
					<Marker position={[this.state.lat, this.state.lng]}>
						<Popup>This is the center <br /> Position: {this.state.lat}, {this.state.lng}</Popup>
					</Marker>
					{this.listOfMarkers.map((marker) => marker)}

					{/* <Marker position={[11043, this.state.lng]}/> */}
				</MapContainer>
			</>
		);
	}
}

export default Map;