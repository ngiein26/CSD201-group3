#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Flowchart: SearchByTitle Algorithm - searchByTitle(String keyword)
Using Graphviz (DOT format)
"""

graphviz_code = """
digraph SearchByTitle {
    rankdir=TB;
    splines=ortho;
    nodesep=0.5;
    ranksep=0.8;
    
    node [fontname="Arial", fontsize=10, style="rounded,filled"];
    edge [fontname="Arial", fontsize=9];
    
    // Start/End nodes (ellipse)
    start [label="Start\nsearchByTitle(keyword)", shape=ellipse, fillcolor="#E8E8E8"];
    end [label="End", shape=ellipse, fillcolor="#E8E8E8"];
    
    // Decision nodes (diamond)
    check_keyword [label="keyword == null\nor empty?", shape=diamond, fillcolor="#D3D3D3"];
    check_empty [label="History\nempty?", shape=diamond, fillcolor="#D3D3D3"];
    check_loop [label="temp !=\nnull?", shape=diamond, fillcolor="#D3D3D3"];
    check_contains [label="title.toLowerCase()\ncontains\nlowerKeyword?", shape=diamond, fillcolor="#D3D3D3"];
    
    // Process nodes (box)
    init_results [label="Create empty\nresults list", shape=box, fillcolor="#F0F0F0"];
    return_empty [label="Return empty\nresults", shape=box, fillcolor="#F0F0F0"];
    prepare [label="lowerKeyword =\nkeyword.toLowerCase()\ntemp = head", shape=box, fillcolor="#F0F0F0"];
    add_result [label="Add temp to\nresults list", shape=box, fillcolor="#F0F0F0"];
    next_node [label="temp = temp.next", shape=box, fillcolor="#F0F0F0"];
    return_results [label="Return\nresults list", shape=box, fillcolor="#F0F0F0"];
    
    // Main flow edges
    start -> check_keyword;
    
    // keyword null/empty branch
    check_keyword -> return_empty [label="Yes"];
    check_keyword -> check_empty [label="No"];
    
    // history empty branch
    check_empty -> return_empty [label="Yes"];
    check_empty -> init_results [label="No"];
    
    // Setup and loop
    init_results -> prepare;
    prepare -> check_loop;
    
    // Loop logic
    check_loop -> check_contains [label="Yes"];
    check_contains -> add_result [label="Yes"];
    check_contains -> next_node [label="No"];
    add_result -> next_node;
    next_node -> check_loop [label="Loop back"];
    
    // Exit loop
    check_loop -> return_results [label="No"];
    return_empty -> end;
    return_results -> end;
}
"""

if __name__ == "__main__":
    print("Graphviz DOT Code for SearchByTitle Flowchart:")
    print("=" * 60)
    print(graphviz_code)
    print("=" * 60)
    print("\nTo render this code to PNG:")
    print("1. Save the DOT code to a file (e.g., 'search_by_title.dot')")
    print("2. Run: dot -Tpng search_by_title.dot -o Flowchart_SearchByTitle.png")
    print("\nOr use Python graphviz library:")
    print("  from graphviz import Digraph")
    print("  ... (parse and render the DOT code)")
