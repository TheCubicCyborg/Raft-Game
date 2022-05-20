package Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.csds.marineradrift.Entry;
import com.csds.marineradrift.Map;

import WorldMap.Chunk;

public class Raft extends Entity{
	
	private Map<Vector2, RaftTile> raft;
	private Polygon hitBox;
	
	public Raft(Chunk c, float x, float y, float w, float h, SpriteBatch b)
	{
		super(c,x,y,w,h,b);
		raft = new Map<Vector2, RaftTile>();
		initiateRaft(b);
		
	}
	
	public void render()
	{
		for(Entry<Vector2, RaftTile> e : raft)
		{
			e.getElement().render();
		}
		chunkMovementCheck();
	}
	
	public void moveRaft(Vector2 v)
	{
		pos.x += v.x;
		pos.y += v.y;
		hitBox.setPosition(pos.x, pos.y);
	}
	
	public void initiateRaft(SpriteBatch b)
	{
		RaftTile m = new RaftTile(this, 1, 0, 0, 0, b, null,null,null,null );
		RaftTile r = new RaftTile(this, 1, 0, 1, 0, b, null,null,null,null );
		RaftTile ur = new RaftTile(this, 1, 0, 1, 1, b, null,null,null,null );
		RaftTile u = new RaftTile(this, 1, 0, 0, 1, b, null,null,null,null );
		RaftTile ul = new RaftTile(this, 1, 0, -1, 1, b, null,null,null,null );
		RaftTile l = new RaftTile(this, 1, 0, -1, 0, b, null,null,null,null );
		RaftTile dl = new RaftTile(this, 1, 0, -1, -1, b, null,null,null,null );
		RaftTile d = new RaftTile(this, 1, 0, 0, -1, b, null,null,null,null );
		RaftTile dr = new RaftTile(this, 1, 0, 1, -1, b, null,null,null,null );
		
		m.setNorth(u);
		m.setEast(r);
		m.setSouth(d);
		m.setWest(l);
		
		r.setWest(m);
		r.setNorth(ur);
		r.setSouth(dr);
		
		ur.setWest(u);
		ur.setSouth(r);
		
		u.setSouth(m);
		u.setWest(ul);
		u.setEast(ur);
		
		ul.setEast(u);
		ul.setSouth(l);
		
		l.setEast(m);
		l.setNorth(ul);
		l.setSouth(dl);
		
		dl.setNorth(l);
		dl.setEast(d);
		
		d.setWest(dl);
		d.setNorth(m);
		d.setEast(dr);
		
		dr.setWest(d);
		dr.setNorth(r);
		
		raft.add(new Vector2(0,0), m);
		raft.add(new Vector2(1,0), r);
		raft.add(new Vector2(1,1), ur);
		raft.add(new Vector2(0,1), u);
		raft.add(new Vector2(-1,1), ul);
		raft.add(new Vector2(-1,0), l);
		raft.add(new Vector2(-1,-1), dl);
		raft.add(new Vector2(0,-1), d);
		raft.add(new Vector2(1,-1), dr);
	
		hitBox = new Polygon(new float[] {dl.pos.x * Chunk.tileSize, dl.pos.y * Chunk.tileSize, 
				(dr.pos.x + 1) * Chunk.tileSize, dr.pos.y * Chunk.tileSize,
				(ur.pos.x + 1) * Chunk.tileSize, (ur.pos.y + 1) * Chunk.tileSize,
				ul.pos.x * Chunk.tileSize, (ul.pos.y + 1) * Chunk.tileSize});
	}
	
	
	private boolean isCollision(Polygon p, Rectangle r) {
	    Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width,
	            r.height, 0, r.height });
	    rPoly.setPosition(r.x, r.y);
	    if (Intersector.intersectPolygonEdges(new FloatArray(rPoly.getVertices()), new FloatArray( p.getVertices()))
	    		|| p.contains(r.x, r.y))
	        return true;
	    return false;
	}
	
	public boolean overlaps(Rectangle r)
	{
		return isCollision(hitBox, r);
	}
}
