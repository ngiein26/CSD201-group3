#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Flowchart: Data Generator Mechanism - generateDummyData(int numberOfPages)
Using Graphviz (DOT format)
"""

graphviz_code = """
digraph DataGenerator {
    rankdir=TB;
    splines=ortho;
    nodesep=0.5;
    ranksep=0.8;
    
    node [fontname="Arial", fontsize=10, style="rounded,filled"];
    edge [fontname="Arial", fontsize=9];
    
    // Start/End nodes (ellipse)
    start [label="Start\ngenerateDummyData(n)", shape=ellipse, fillcolor="#E8E8E8"];
    end [label="End", shape=ellipse, fillcolor="#E8E8E8"];
    
    // Decision nodes (diamond)
    check_n [label="numberOfPages\n<= 0?", shape=diamond, fillcolor="#D3D3D3"];
    check_loop [label="i <\nnumberOfPages?", shape=diamond, fillcolor="#D3D3D3"];
    
    // Process nodes (box)
    init [label="Initialize:\nstartTime = now() - n sec\nwithNano(0)", shape=box, fillcolor="#F0F0F0"];
    init_loop [label="i = 0\n(Loop start)", shape=box, fillcolor="#F0F0F0"];
    random_set [label="index = random(0-7)\nurl = sampleUrls[index]\ntitle = sampleTitles[index] +\n\"#\" + (i+1)", shape=box, fillcolor="#F0F0F0"];
    calc_time [label="timestamp = startTime +\n(i+1) seconds", shape=box, fillcolor="#F0F0F0"];
    visit_call [label="visit(url, title,\ntimestamp)", shape=box, fillcolor="#F0F0F0"];
    increment [label="i++", shape=box, fillcolor="#F0F0F0"];
    return_msg [label="Return with\ni pages added", shape=box, fillcolor="#F0F0F0"];
    
    // Main flow edges
    start -> check_n;
    check_n -> init [label="No"];
    check_n -> return_msg [label="Yes"];
    
    init -> init_loop;
    init_loop -> check_loop;
    check_loop -> random_set [label="Yes"];
    random_set -> calc_time;
    calc_time -> visit_call;
    visit_call -> increment;
    increment -> check_loop [label="Loop back"];
    
    check_loop -> return_msg [label="No"];
    return_msg -> end;
}
"""

if __name__ == "__main__":
    print("Graphviz DOT Code for Data Generator Flowchart:")
    print("=" * 60)
    print(graphviz_code)
    print("=" * 60)
    print("\nTo render this code to PNG:")
    print("1. Save the DOT code to a file (e.g., 'data_generator.dot')")
    print("2. Run: dot -Tpng data_generator.dot -o Flowchart_DataGenerator.png")
    print("\nOr use Python graphviz library:")
    print("  from graphviz import Digraph")
    print("  ... (parse and render the DOT code)")
