package com.androidprojects.tudevs.tu_orgnzr.WeatherAnalyzer;

import com.androidprojects.tudevs.tu_orgnzr.Profile_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan Grigorov on 30/04/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
public class TreeConstructor {

    private class Node {
        private String name;
        private JSONArray children;
        private Collection<Node> extractedChildren;
        boolean isLeaf;
        Map leaf = new HashMap<String,String>();

        public void setName(String name) {
            this.name = name;
        }

        public void setChildren(JSONArray children) {
            this.children = children;
        }

        public void setRouteToNode(String routeToNode) {
            this.routeToNode = routeToNode;
        }

        private String routeToNode;

        public Node(String name, JSONArray children, String routeToNode, boolean isLeaf) {
            this.name = name;
            this.children = children;
            this.routeToNode = routeToNode;
            this.isLeaf = isLeaf;
            this.extractedChildren = new ArrayList<Node>();

        }
    }

    public Node root;
    private JSONObject treeJSON;

    public TreeConstructor() {
        try {
            treeJSON = new JSONObject(Profile_Activity.loadJSONFromFile("DecisionTree.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //JSONObject treeJSON =  new JSONObject(Profile_Activity.loadJSONFromFile("DecisionTree.json")) ;

    //public void createNode(String name, )
    public void setRootNode() {
        try {
            String rootName = treeJSON.getString("Name");
            JSONArray rootChildren = treeJSON.getJSONArray("Children");
            boolean isLeaf = true;

            if (rootChildren.length() != 0) {
                isLeaf = false;
            }

            String routeToNode = treeJSON.getString("ParentTransition");
            JSONObject leafInfo = treeJSON.getJSONObject("LeafInf");
            this.root = new Node(rootName, rootChildren, routeToNode, isLeaf);
            Iterator keys = leafInfo.keys();
            while (keys.hasNext()){
                String key = (String) keys.next();
                this.root.leaf.put(key, leafInfo.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fillTreeFromJSON(Node root) {
        try {
            for (int i = 0; i < root.children.length(); i++) {
                JSONObject tmpObject = root.children.getJSONObject(i);
                String rootName = tmpObject.getString("Name");
                JSONArray rootChildren = tmpObject.getJSONArray("Children");
                boolean isLeaf = true;

                if (rootChildren.length() != 0) {
                    isLeaf = false;
                }

                String routeToNode = treeJSON.getString("ParentTransition");
                JSONObject leafInfo = treeJSON.getJSONObject("LeafInf");
                Node child = new Node(rootName, rootChildren, routeToNode, isLeaf);
                Iterator keys = leafInfo.keys();
                while (keys.hasNext()){
                    String key = (String) keys.next();
                    root.leaf.put(key, leafInfo.getString(key));
                }

                root.extractedChildren.add(child);
                for (int index = 0; index < child.children.length(); index++) {
                    fillTreeFromJSON(child);
                }
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String generateOutcome(Node currentNode, String[] weatherValues, int indexOfValue) {
        int tmpIndex  = indexOfValue;
        if (currentNode.leaf.containsKey(weatherValues[tmpIndex])) {
            return (String)currentNode.leaf.get(weatherValues[tmpIndex]);
        }
        for (Node node: currentNode.extractedChildren) {
            return generateOutcome(node, weatherValues, tmpIndex++);
        }
        return null;
    }
}
