package com.example.mapsapplication

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.LegStep
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxandroiddemo.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import java.util.ArrayList
import java.util.List
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import static com.mapbox.api.directions.v5.DirectionsCriteria.GEOMETRY_POLYLINE
import static com.mapbox.mapboxsdk.style.layers.Property.LINE_CAP_ROUND
import static com.mapbox.mapboxsdk.style.layers.Property.LINE_JOIN_ROUND
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineOpacity
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth

public class SnakingDirectionsRouteActivity extends AppCompatActivity
implements OnMapReadyCallback {

    private static final float NAVIGATION_LINE_WIDTH = 6;
    private static final float NAVIGATION_LINE_OPACITY = .8f;
    private static final String DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID = "DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID";
    private static final String DRIVING_ROUTE_POLYLINE_SOURCE_ID = "DRIVING_ROUTE_POLYLINE_SOURCE_ID";
    private static final int DRAW_SPEED_MILLISECONDS = 500;
// Origin point in Paris, France
    private static final Point PARIS_ORIGIN_POINT = Point.fromLngLat(2.35222, 48.856614);

// Destination point in Lyon, France
    private static final Point LYON_DESTINATION_POINT = Point.fromLngLat(4.83565, 45.76404);

    private MapView mapView;
    private MapboxMap mapboxMap;
    private MapboxDirections mapboxDirectionsClient;
    private Handler handler = new Handler ();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_javaservices_snaking_directions_route);

// Setup the MapView
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }
}