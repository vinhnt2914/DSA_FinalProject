// Essential for the map to work
// including react and react-leaflet
import React, { Component } from 'react'
// Individual components
import { CRS } from 'leaflet'
import {
  ImageOverlay,
  MapContainer,
  Marker,
  Popup,
  ZoomControl,
} from 'react-leaflet'
import '../App.css'
import DebugMode from './DebugMode'
import MapCustom from './MapCustom'
import MapCustom2 from './MapCustom2'
import PlaceManagement from './PlaceManagement'
import AddPlace from "./AddPlace";

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
    super(props)
    this.state = {
      mapData: {
        lat: 51.05,
        lng: +0.09,
        zoom: 0.45,
      },
      debugData: {
        lat: 0,
        lng: 0,
        zoom: 0,
      },
    }
    // this.moveTo = this.moveTo.bind(this);
    this.setCenter = this.setCenterProps.bind(this)
    this.listOfMarkers = []
    this.PointsOfBound = []
    // this.debugData = this.initDataForDebug()
    // this.setDebugData = this.setDebugData.bind(this)
  }

  // TODO: Implement the debug mode
  initDataForDebug() {
    return {
      lat: this.state.mapData.lat,
      lng: this.state.mapData.lng,
      zoom: this.state.mapData.zoom,
    }
  }

  setDebugData(lat, lng, zoom) {
    //
    this.debugData = {
      lat: lat,
      lng: lng,
      zoom: zoom,
    }
    console.table('Debug data updated: ', this.debugData)
  }

  onBoundDefined(topLeft, topRight, bottomLeft, bottomRight) { }

  componentDidMount() {
    // this.initDataForDebug()
  }

  componentDidUpdate(prevProps) {
    if (this.props.state !== prevProps.state) {
      this.setCenterProps(
        this.props.state.lng,
        this.props.state.lat,
        this.props.state.zoom
      )
      this.PointsOfBound = this.props.PointsOfBound
      if (this.props.markers) {
        this.fetchMarkers(this.props.state.markers)
        // calculate the number of markers
      }
    }
  }

  // TODO: Get a list of nearby places

  setCenterProps(longitude, latitude, zoom) {
    this.setState(
      {
        mapData: {
          lat: latitude,
          lng: longitude,
          zoom: zoom,
        },
        debugData: {
          lat: latitude,
          lng: longitude,
          zoom: zoom,
        },
      },
      () => {
        this.setDebugData(this.state.lat, this.state.lng, this.state.zoom)
        console.log('New debug data: ', this.debugData)
      }
    )
  }
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
      )
    })
  }

  /**
   * Renders the Map component.
   * @returns {JSX.Element} The rendered Map component.
   */
  render() {
    const bounds = [
      [0, 0],
      [10000000, 10000000],
    ]
    // console.log(
    //   'Current state: ',
    //   this.state.mapData.lat,
    //   this.state.mapData.lng,
    //   this.state.mapData.zoom
    // )
    console.info('[render] debugData: ', this.debugData)
    return (
      <>
        {/* Locate the debug button at the bottom right */}
        <div className="absolute bottom-0 right-0 m-8     z-30">
          <AddPlace ClassProperties={"mr-4"}/>
          <PlaceManagement ClassProperties={"mr-4"}/>
          <DebugMode setOfData={this.state.debugData} />
          {/* <InfoDiag /> */}
        </div>
        {/* The main map */}
        <MapContainer
          center={[this.state.mapData.lat, this.state.mapData.lng]}
          zoom={this.state.mapData.zoom}
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
          <MapCustom
            lat={this.state.mapData.lat}
            lng={this.state.mapData.lng}
            zoom={this.state.mapData.zoom}
          />
          <ImageOverlay
            url="https://preview.redd.it/ih6no69aj90y.png?auto=webp&s=8e4f9101f58e0812f3625a51ec65c9b7c050da75"
            bounds={bounds}
            className="border box-border border-black"
          />
          <ZoomControl position="bottomleft" />
          <Marker position={[this.state.mapData.lat, this.state.mapData.lng]}>
            <Popup>
              This is the center <br /> Position: {this.state.mapData.lat},{' '}
              {this.state.mapData.lng}
            </Popup>
          </Marker>
          {this.listOfMarkers.map((marker) => marker)}

          {/* <Marker position={[11043, this.state.lng]}/> */}
        </MapContainer>
      </>
    )
  }
}

export default Map
