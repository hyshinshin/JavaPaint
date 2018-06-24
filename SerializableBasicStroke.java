/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finishedpaint;

/*
Copyright 2006 Thomas Hawtin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
public class SerializableBasicStroke extends java.awt.BasicStroke implements java.io.Serializable
{
     private static class Serial implements java.io.Serializable {
         static final long serialVersionUID = 5538700973722429161L+1;
         private transient SerializableBasicStroke replacement;

         Serial(SerializableBasicStroke replacement) {
             this.replacement = replacement;
         }

         private void writeObject(
             java.io.ObjectOutputStream out
         ) throws java.io.IOException {
             out.writeFloat(replacement.getLineWidth());
             out.writeInt(replacement.getEndCap());
             out.writeInt(replacement.getLineJoin());
             out.writeFloat(replacement.getMiterLimit());
             out.writeUnshared(replacement.getDashArray());
             out.writeFloat(replacement.getDashPhase());
         }
         private void readObject(
             java.io.ObjectInputStream in
         ) throws java.io.IOException, java.lang.ClassNotFoundException {
             try {
                 this.replacement = new SerializableBasicStroke(
                     in.readFloat(),             // lineWidth
                     in.readInt(),               // endCap
                     in.readInt(),               // lineJoin
                     in.readFloat(),             // miterLimit
                     (float[])in.readUnshared(), // dashArray
                     in.readFloat()              // dashPhase
                 );
             } catch (IllegalArgumentException exc) {
                 java.io.InvalidObjectException wrapper =
                     new java.io.InvalidObjectException(exc.getMessage());
                 wrapper.initCause(exc);
                 throw wrapper;
             }
         }

         private Object readResolve() throws java.io.ObjectStreamException {
             return replacement;
         }
     }


     public static java.awt.BasicStroke serializable(
         java.awt.BasicStroke target
     ) {
         return (target instanceof java.io.Serializable) ?
             target :
             new SerializableBasicStroke(
                 target.getLineWidth(),
                 target.getEndCap(),
                 target.getLineJoin(),
                 target.getMiterLimit(),
                 target.getDashArray(),
                 target.getDashPhase()
             );
     }

     public SerializableBasicStroke() {
         super();
     }

     public SerializableBasicStroke(
         float lineWidth
     ) {
         super(
             lineWidth
         );
     }

     public SerializableBasicStroke(
         float lineWidth,
         int endCap,
         int lineJoin
     ) {
         super(
             lineWidth,
             endCap,
             lineJoin
         );
     }

     public SerializableBasicStroke(
         float lineWidth,
         int endCap,
         int lineJoin,
         float miterLimit
     ) {
         super(
             lineWidth,
             endCap,
             lineJoin,
             miterLimit
         );
     }

     public SerializableBasicStroke(
         float lineWidth,
         int endCap,
         int lineJoin,
         float miterLimit,
         float[] dashArray,
         float dashPhase
     ) {
         super(
             lineWidth,
             endCap,
             lineJoin,
             miterLimit,
             dashArray,
             dashPhase
         );
     }

     private Object writeReplace() throws java.io.ObjectStreamException {
         return new Serial(this);
     }
}