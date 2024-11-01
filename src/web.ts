import { WebPlugin } from '@capacitor/core';

import type { NoiseMeterPlugin } from './definitions';

export class NoiseMeterWeb extends WebPlugin implements NoiseMeterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async start(): Promise<void> {
      console.log("Start!")
  }

  async startRecording(): Promise<void> {
      console.log("Recording!")
  }

  async stop(): Promise<void> {
      console.log("Stop!")
  }

  async getNoiseLevel(): Promise<{ decibels: number; }> {
      console.log("Noiselevel");
      const decibels = 50;
      return { decibels }; 
  }

}
